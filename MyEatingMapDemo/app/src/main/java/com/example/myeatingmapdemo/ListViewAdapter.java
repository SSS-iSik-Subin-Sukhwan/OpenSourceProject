package com.example.myeatingmapdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
  private ArrayList<ListViewItem> listViewItemList;

  private TextView POITextView;
  private TextView AddressTextView;

  public ListViewAdapter() {
    listViewItemList = new ArrayList<ListViewItem>();

    POITextView = null;
    AddressTextView = null;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final Context context = parent.getContext();
    View tempView = convertView;

    if (tempView == null) tempView = getConvertView(context, parent);

    getTextView(tempView);
    setTextView(listViewItemList.get(position));

    return tempView;
  }

  private View getConvertView(Context context, ViewGroup parent) {
    View convertView;

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    convertView = inflater.inflate(R.layout.listview_item, parent, false);

    return convertView;
  }

  private void getTextView(View convertView) {
    POITextView = (TextView) convertView.findViewById(R.id.textView1);
    AddressTextView = (TextView) convertView.findViewById(R.id.textView2);
  }

  private void setTextView(ListViewItem item) {
    POITextView.setText(item.getPOIStr());
    AddressTextView.setText(item.getAddressStr());
  }

  @Override
  public int getCount() { return listViewItemList.size(); }

  @Override
  public long getItemId(int position) { return position; }

  @Override
  public Object getItem(int position) { return listViewItemList.get(position); }

  public void addItem(String POI, String address, double latitude, double longitude) {
    ListViewItem item = new ListViewItem();

    item.setPOIStr(POI);
    item.setAddressStr(address);
    item.setPOIlatitude(latitude);
    item.setPOIlongitude(longitude);

    listViewItemList.add(item);
  }
}