package com.example.nsapplication.IPPTInventory;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<Vocation> getVocationList()
    {
        List<Vocation> vocationList = new ArrayList<>();

        Vocation combatService = new Vocation();
        combatService.setName("Combat Service");
        vocationList.add(combatService);

        Vocation Commando = new Vocation();
        Commando.setName("Commando");
        vocationList.add(Commando);

        Vocation Driver = new Vocation();
        Driver.setName("Driver");
        vocationList.add(Driver);

        Vocation Guard = new Vocation();
        Guard.setName("Guard");
        vocationList.add(Guard);

        return vocationList;
    }

}
