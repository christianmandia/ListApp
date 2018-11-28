package cifprodolfoucha.com.listapp.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cifprodolfoucha.com.listapp.Modelos.Articulo;
import cifprodolfoucha.com.listapp.R;

public class Adaptador_ListaRV extends RecyclerView.Adapter<Adaptador_ListaRV.ReciclerViewHolder_Lista> implements View.OnClickListener/*,View.OnLongClickListener*/{

    private ArrayList<Articulo> mArticulos;
    private Context mContext;

    private View.OnClickListener listener;
    //private View.OnLongClickListener longlistener;
/*
    public Adapatador_Lista(Context context, ArrayList<Articulo>articulos) {
        super(context, R.layout.layout_elemento_lista);       // Enviamos o layout que imos utilizar

        this.articulos = articulos;
        this.mContext = context;
    }

    */
    public Adaptador_ListaRV(ArrayList<Articulo> articulos){
        this.mArticulos = articulos;
        //this.mContext = context;
    }





    @Override
    public ReciclerViewHolder_Lista onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);

        //View v=inflater.inflate(R.layout.layout_elemento_lista,parent,false);
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

/*
    @Override
    public boolean onLongClick(View v) {
        //return longlistener.onLongClick(v);
        v.onLo
        return false;
    }

    public void setOnLongClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
*/

    @Override
    public void onBindViewHolder(Adaptador_ListaRV.ReciclerViewHolder_Lista holder, int position) {
        //holder.bind(mArticulos.get(position));
        Articulo articulo=mArticulos.get(position);


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

        if(articulo.getNotas().length()!=0){

        }else{

        }

        if(articulo.isMarcado()){
            //v.setBackgroundColor(0xFF00FF00);

            v.setBackgroundColor(0xFF008080);

        }else{
            v.setBackgroundColor(0xFF00FFFF);
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
                    if(mArticulos.get(posicion).isSeleccionado()) {
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
