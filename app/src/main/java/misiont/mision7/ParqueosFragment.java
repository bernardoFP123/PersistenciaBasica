package misiont.mision7;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

public class ParqueosFragment extends android.app.Fragment {

    ViewGroup container;
    FloatingActionButton fabAddRegister;
    RecyclerView recyclerViewRegistros;
    RecyclerView.LayoutManager layoutManager;
    RegisterParkingOperations registerParkingOperations;
    RecyclerViewAdapterRegistros adapterRegistros;
    List<Registro> registos;

    public ParqueosFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ParqueosFragment newInstance() {
        ParqueosFragment fragment = new ParqueosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parqueos, container, false);
        fabAddRegister = view.findViewById(R.id.fabAddRegister);
        fabAddRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ContentActivity)getActivity()).showAlertForNewRegister(registos,adapterRegistros);
            }
        });

        recyclerViewRegistros = view.findViewById(R.id.recyclerViewRegistros);
        registos = registerParkingOperations.getRegisters();
        adapterRegistros = new RecyclerViewAdapterRegistros(getActivity().getApplicationContext(),registos);
        layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerViewRegistros.setAdapter(adapterRegistros);
        recyclerViewRegistros.setLayoutManager(layoutManager);

        this.container = container;


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            registerParkingOperations = (RegisterParkingOperations) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException("Se necesita implementar la interfaz " + registerParkingOperations.toString());
        }
    }


    public void updateRecyclerView(){
        registos = new ArrayList<Registro>();
        adapterRegistros = new RecyclerViewAdapterRegistros(getActivity().getApplicationContext(),registos);
        recyclerViewRegistros.setAdapter(adapterRegistros);
        adapterRegistros.notifyDataSetChanged();
    }



}
