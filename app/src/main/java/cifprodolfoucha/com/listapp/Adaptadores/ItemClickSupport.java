package cifprodolfoucha.com.listapp.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cifprodolfoucha.com.listapp.R;

public class ItemClickSupport {

    /**
     * Esta é unha clase que implmentei para poder realizar o LongClick sobre o RecyclreView.
     **/


    /**
     * Referencia RecyclerView.
     **/
    private final RecyclerView mRecyclerView;
    /**
     * Referencia ao obxecto OnItemClickListener.
     **/
    private OnItemClickListener mOnItemClickListener;
    /**
     * Referencia ao obxecto OnItemLongClickListener.
     **/
    private OnItemLongClickListener mOnItemLongClickListener;


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };


    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };


    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };


    private ItemClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }


    /**
     *
     * @param view o RecyclerView ao cal se lle quere asociar o CLick ou o LongClick.
     * @return
     **/
    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    /**
     *
     * @param view o RecyclerView ao cal se lle quere eliminar a asociacion de CLick ou de LongClick.
     * @return
     **/

    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }


    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }


    /**
     *
     * @param listener
     * @return 
     **/
    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }


    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }


    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }


    public interface OnItemLongClickListener {

        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
