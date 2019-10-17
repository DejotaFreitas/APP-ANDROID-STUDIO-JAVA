package com.dejota.petadote.global;

import com.dejota.petadote.entity.Pet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PetsGlobal {

    private static Map<Integer, Pet> pets = new LinkedHashMap<>();

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
        Integer[] listKeys = pets.keySet().toArray(new Integer[0]);
        List<Pet> listaPorDemanda = new ArrayList<>();
        for (int i = inicio; i < listKeys.length && i <= fim; i++){
                listaPorDemanda.add(pets.get(listKeys[i]));
        }
        return listaPorDemanda;
    }

}
