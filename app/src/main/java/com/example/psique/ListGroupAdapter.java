package com.example.psique;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.models.Group;

import java.util.List;

public class ListGroupAdapter extends RecyclerView.Adapter<ListGroupAdapter.ListGroupViewHolder> {

    private List<Group> groups;
    private Context context;

    public ListGroupAdapter(List<Group> groups, Context context){
        this.groups=groups;
        this.context=context;
    }

    @NonNull
    @Override
    public ListGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ListGroupViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_group,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListGroupViewHolder holder, int position){
        holder.bind(groups.get(position));
    }

    @Override
    public int getItemCount(){ return groups.size();}

    class ListGroupViewHolder extends RecyclerView.ViewHolder{
        TextView tv_listGroupName;
        LinearLayout ll_listGroup;

        public ListGroupViewHolder(@NonNull View itemView){
            super(itemView);
            tv_listGroupName=itemView.findViewById(R.id.tv_listGroupName);
            ll_listGroup=itemView.findViewById(R.id.ll_groupContainer);
        }

        public void bind(Group group){
            tv_listGroupName.setText(group.getName());
             ll_listGroup.setOnClickListener(view -> ChatActivity.start(context, group.getGuid()));
        }
    }
}
