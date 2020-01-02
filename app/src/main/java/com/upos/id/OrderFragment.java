package com.upos.id;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.upos.id.Models.Kategori;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


//    public OrderFragment() {
//        // Required empty public constructor
//    }


    private boolean mTwoPane;
    RequestQueue requestQueue;
    List<Kategori> kategoriList;
    SimpleItemRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        // Inflate the layout for this fragment

        if (rootView.findViewById(R.id.category_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();


        View recyclerView = rootView.findViewById(R.id.category_list);
        kategoriList = new ArrayList<>();

        getJsonData("https://mksrobotics.web.app/admin/api/kategori");
        adapter = new SimpleItemRecyclerViewAdapter(this, kategoriList, mTwoPane);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        return rootView;
    }

    private void getJsonData(String url){

        final JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                url, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("category", response.toString());
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObj =  response.getJSONObject(i);
                        Kategori kategori = new Kategori();
                        kategori.setNama(jsonObj.getString("nama"));
                        kategori.setKode(jsonObj.getString("kode"));
                        kategori.setKeterangan(jsonObj.getString("keterangan"));
                        kategoriList.add(kategori);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Volley", error.toString());
            }
        });
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrReq);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(adapter);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final OrderFragment mParentFragment;
        private final List<Kategori> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Kategori item = (Kategori) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemListFragment.ARG_ITEM_ID, item.kode);
                    ItemListFragment fragment = new ItemListFragment();
                    fragment.setArguments(arguments);
                    mParentFragment.getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.category_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, categoryDetailActivity.class);
                    intent.putExtra(ItemListFragment.ARG_ITEM_ID, item.keterangan);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(OrderFragment parent,
                                      List<Kategori> items,
                                      boolean twoPane) {
            mValues = items;
            mParentFragment= parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mNameView.setText(mValues.get(position).nama);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mNameView;


            ViewHolder(View view) {
                super(view);
                mNameView = (TextView) view.findViewById(R.id.nameTxt);
            }
        }
    }

}
