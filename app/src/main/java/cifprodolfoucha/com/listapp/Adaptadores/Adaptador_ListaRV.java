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

    private ArrayList<Loxica_Articulo> mLoxicaArticulos;
    private Context mContext;

    private View.OnClickListener listener;
    //private View.OnLongClickListener longlistener;
/*
    public Adapatador_Lista(Context context, ArrayList<Loxica_Articulo>loxicaArticulos) {
        super(context, R.layout.layout_elemento_lista);       // Enviamos o layout que imos utilizar

        this.loxicaArticulos = loxicaArticulos;
        this.mContext = context;
    }

    */
    public Adaptador_ListaRV(ArrayList<Loxica_Articulo> loxicaArticulos){
        if(loxicaArticulos !=null) {
            this.mLoxicaArticulos = loxicaArticulos;
        }else{
            this.mLoxicaArticulos =new ArrayList<Loxica_Articulo>();
        }
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
        //holder.bind(mLoxicaArticulos.get(position));
        Loxica_Articulo loxicaArticulo = mLoxicaArticulos.get(position);


        View v=holder.itemView;
        CheckedTextView mCheck=holder.mCTVArticulo;
        TextView mCantidad=holder.mTVCantidadArticulo;
        TextView mPrecio=holder.mTVPrecioArticulo;
        ImageView mImagen=holder.mIVImagenArticulo;


        mCheck.setText(loxicaArticulo.getNombre());


        if(loxicaArticulo.getCantidad()!=0) {
            mCantidad.setText(loxicaArticulo.getCantidad()+"");
        }else{
            mCantidad.setText("1");
        }

        if(loxicaArticulo.getPrecio()!=0) {
            mPrecio.setText(loxicaArticulo.getPrecio() + "€");
        }else{
            mPrecio.setText("");
        }

        if(loxicaArticulo.getNotas().length()!=0){

        }else{

        }

        if(loxicaArticulo.isMarcado()){
            //v.setBackgroundColor(0xFF00FF00);

            v.setBackgroundColor(0xFF008080);

        }else{
            v.setBackgroundColor(0xFF00FFFF);
        }

        if(loxicaArticulo.isSeleccionado()){
            mCheck.setChecked(true);
        }else{
            mCheck.setChecked(false);
        }

        if(!loxicaArticulo.riExiste()){
            Bitmap bitmap = BitmapFactory.decodeFile(loxicaArticulo.getRutaImagen());
            mImagen.setImageBitmap(bitmap);
        }else{
            Bitmap bitmap = BitmapFactory.decodeFile(null);
            mImagen.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getItemCount() {

        return mLoxicaArticulos.size();
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
                    if(mLoxicaArticulos.get(posicion).isSeleccionado()) {
                        mLoxicaArticulos.get(posicion).setSeleccionado(false);
                    }else{
                        mLoxicaArticulos.get(posicion).setSeleccionado(true);
                    }

                    notifyDataSetChanged();
                }
            });
        }
        */
    }

}
