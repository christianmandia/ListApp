package cifprodolfoucha.com.listapp.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Loxica.Loxica_Articulo;
import cifprodolfoucha.com.listapp.R;

public class Adaptador_ListaRV extends RecyclerView.Adapter<Adaptador_ListaRV.ReciclerViewHolder_Lista> implements View.OnClickListener/*,View.OnLongClickListener*/{

    private ArrayList<Loxica_Articulo> mArticulos;
    private Context mContext;

    private View.OnClickListener listener;

    public Adaptador_ListaRV(ArrayList<Loxica_Articulo> articulos){
        if(articulos !=null) {
            this.mArticulos = articulos;
        }else{
            this.mArticulos =new ArrayList<Loxica_Articulo>();
        }
    }

    @Override
    public ReciclerViewHolder_Lista onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.layout_elemento_listacimagen,parent,false);

        v.setOnClickListener(this);

        ReciclerViewHolder_Lista viewHolderLista=new ReciclerViewHolder_Lista(v);
        return viewHolderLista;
    }

    @Override
    public void onClick(View v) {
            if(listener!=null)
                listener.onClick(v);
    }

    public void setOnClickListener(View.OnClickListener listener){
            this.listener=listener;
    }

    @Override
    public void onBindViewHolder(Adaptador_ListaRV.ReciclerViewHolder_Lista holder, int position) {
        Loxica_Articulo articulo = mArticulos.get(position);


        View v=holder.itemView;
        CheckedTextView mCheck=holder.mCTVArticulo;
        TextView mCantidad=holder.mTVCantidadArticulo;
        TextView mPrecio=holder.mTVPrecioArticulo;
        ImageView mImagen=holder.mIVImagenArticulo;


        mCheck.setText(articulo.getNombre());


        if(articulo.getCantidad()!=0) {
            mCantidad.setText(articulo.getCantidad()+"");
        }else{
            mCantidad.setText("1");
        }

        if(articulo.getPrecio()!=0) {
            mPrecio.setText(articulo.getPrecio() + "€");
        }else{
            mPrecio.setText("");
        }

        if(articulo.isMarcado()){
            //v.setBackgroundColor(0xFF00FF00);

            v.setBackgroundColor(0xFF008080);

        }else{
            //v.setBackgroundColor(0xFF00FFFF);
            //v.setBackgroundColor(0xDD4B92FF);
            v.setBackgroundColor(0xFF0099CC);
        }

        if(articulo.isSeleccionado()){
            mCheck.setChecked(true);
        }else{
            mCheck.setChecked(false);
        }

        if(!articulo.riExiste()){
            Bitmap bitmap = BitmapFactory.decodeFile(articulo.getRutaImagen());
            mImagen.setImageBitmap(bitmap);
        }else{
            Bitmap bitmap = BitmapFactory.decodeFile(null);
            mImagen.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getItemCount() {

        return mArticulos.size();
    }

    public class ReciclerViewHolder_Lista extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{

        public CheckedTextView mCTVArticulo;
        public TextView mTVCantidadArticulo;
        public TextView mTVPrecioArticulo;
        /**/
        public ImageView mIVImagenArticulo;



        public ReciclerViewHolder_Lista(View itemView) {
            super(itemView);

            /*
            mCTVArticulo=(CheckedTextView)itemView.findViewById(R.id.ctvNombreArticulo_ElementoLista);
            mTVCantidadArticulo=(TextView)itemView.findViewById(R.id.tvCantidadArticuloLista_ElementoLista);
            mTVPrecioArticulo=(TextView)itemView.findViewById(R.id.tvPrecioArticuloLista_ElementoLista);
            */
            mCTVArticulo=(CheckedTextView)itemView.findViewById(R.id.ctvNombreArticulo_ElementoLista2);
            mTVCantidadArticulo=(TextView)itemView.findViewById(R.id.tvCantidadArticuloLista_ElementoLista2);
            mTVPrecioArticulo=(TextView)itemView.findViewById(R.id.tvPrecioArticuloLista_ElementoLista2);
            /**/
            mIVImagenArticulo=(ImageView)itemView.findViewById(R.id.ivImagenArticulo_ElementoLista2);
        }
/*
        @Override
        public void onClick(View v) {
            mCTVArticulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posicion=getAdapterPosition();
                    if(marticulos.get(posicion).isSeleccionado()) {
                        mArticulos.get(posicion).setSeleccionado(false);
                    }else{
                        mArticulos.get(posicion).setSeleccionado(true);
                    }

                    notifyDataSetChanged();
                }
            });
        }
        */
    }

}
