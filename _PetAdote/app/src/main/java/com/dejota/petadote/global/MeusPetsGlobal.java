package com.dejota.petadote.global;

import com.dejota.petadote.entity.Pet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeusPetsGlobal {

    private static Map<Integer, Pet> pets = new HashMap<>();

    public static boolean exist(Integer petId){
        return pets.containsKey(petId);
    }

    public static void add(Integer petId, Pet pet){
        pets.put(petId, pet);
    }

    public static Pet get(Integer key){
        return pets.get(key);
    }

    public static List<Pet> listaPorDemanda(int offsetlist){
        int limit = 21;
        int inicio = limit * offsetlist;
        int fim = inicio + limit;
        List<Integer> listKeys = new ArrayList<>(pets.keySet());
        List<Pet> listaPorDemanda = new ArrayList<>();
        for (int i = inicio; i < listKeys.size() && i < fim; i++){
                listaPorDemanda.add(pets.get(listKeys.get(i)));
        }
        return listaPorDemanda;
    }

}
