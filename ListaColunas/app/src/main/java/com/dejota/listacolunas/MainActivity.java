package com.dejota.listacolunas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewStub stubGrid;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private List<Produto> pordutoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stubGrid = (ViewStub) findViewById(R.id.stub_grid);
        stubGrid.inflate();

        loadProdutosList();
        gridView = (GridView) findViewById(R.id.mygridview);
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, pordutoList);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(clicarItem);
    }

    private void loadProdutosList() {
        pordutoList = new ArrayList<>();
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 0", " Descricão 0"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 1", " Descricão 1"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 2", " Descricão 2"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 3", " Descricão 3"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 4", " Descricão 4"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 5", " Descricão 5"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 6", " Descricão 6"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 7", " Descricão 7"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 8", " Descricão 8"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 9", " Descricão 9"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 10", " Descricão 10"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 11", " Descricão 11"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 12", " Descricão 12"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 13", " Descricão 13"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 14", " Descricão 14"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 15", " Descricão 15"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 16", " Descricão 16"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 17", " Descricão 17"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 18", " Descricão 18"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 19", " Descricão 19"));
        pordutoList.add(new Produto(R.mipmap.ic_launcher, "Produto 20", " Descricão 20"));
    }

    AdapterView.OnItemClickListener clicarItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Produto p = pordutoList.get(i);
            Log.i("LogX", "Nome: "+p.getNome());
        }
    };


}
