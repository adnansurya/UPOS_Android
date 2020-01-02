package com.upos.id;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.upos.id.Models.Produk;
import com.upos.id.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single category detail screen.
 * This fragment is either contained in a {@link categoryListActivity}
 * in two-pane mode (on tablets) or a {@link categoryDetailActivity}
 * on handsets.
 */
public class categoryDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    String filter;
    String nama_produk = "";
    int count = 0;

    RequestQueue requestQueue;
    List<Produk> produkList;
    RecyclerViewAdapter adapter;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
//    public categoryDetailFragment() {
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            filter = getArguments().getString(ARG_ITEM_ID);
//            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
//                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_detail, container, false);
        
        RecyclerView recyclerView = rootView.findViewById(R.id.produk_list);


        produkList = new ArrayList<>();

        getJsonData("https://mksrobotics.web.app/admin/api/filter/data_produk/by/kategori/equals/" + filter);
        adapter = new RecyclerViewAdapter(this, produkList);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        setCount(getActivity(), String.valueOf(count), menu);
        super.onPrepareOptionsMenu(menu);


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
                        Produk produk = new Produk();
                        produk.setNama(jsonObj.getString("nama"));
                        produk.setKode(jsonObj.getString("kode"));
                        produk.setKeterangan(jsonObj.getString("keterangan"));
                        produk.setGambar(jsonObj.getString("gambar"));
                        produk.setHargaJual(jsonObj.getString("hargaJual"));
                        produkList.add(produk);
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
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    public void setCount(Context context, String count, Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.ic_group);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

    public class RecyclerViewAdapter
            extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private final categoryDetailFragment mParentFragment;
        private final List<Produk> mValues;


        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Produk item = (Produk) view.getTag();
                count++;
                Log.e("COUNT", String.valueOf(count));
                mParentFragment.getActivity().invalidateOptionsMenu();




//                Toast.makeText(mParentFragment.getActivity(), "Produk : " + item.nama, Toast.LENGTH_SHORT).show();
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putString(categoryDetailFragment.ARG_ITEM_ID, item.keterangan);
//                    categoryDetailFragment fragment = new categoryDetailFragment();
//                    fragment.setArguments(arguments);
//                    mParentFragment.getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.category_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, categoryDetailActivity.class);
//                    intent.putExtra(categoryDetailFragment.ARG_ITEM_ID, item.keterangan);
//
//                    context.startActivity(intent);
//                }
            }
        };

        RecyclerViewAdapter(categoryDetailFragment parent,
                            List<Produk> items) {
            mValues = items;
            mParentFragment = parent;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.produk_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mNamaView.setText(mValues.get(position).nama);
            if(!mValues.get(position).gambar.isEmpty()){
                String imageUrl = "https://firebasestorage.googleapis.com/v0/b/mksrobotics.appspot.com/o/produk%2F"+ mValues.get(position).gambar + "?alt=media";
                Glide.with(mParentFragment.getContext())
                        .load(imageUrl)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mImageView);
            }

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mNamaView;
            final ImageView mImageView;


            ViewHolder(View view) {
                super(view);
                mNamaView = view.findViewById(R.id.namaTxt);
                mImageView = view.findViewById(R.id.imageView);

            }
        }

    }




}
