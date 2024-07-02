package controller;

import model.classes.Victual;

import java.util.ArrayList;
import java.util.List;

public class VictualController {
    ConnectionHandler conn = new ConnectionHandler();

    public List<Victual> getVictual() {
        conn.connect();

        List<Victual> victuals = new ArrayList<>();


        return victuals;
    }

    public Victual getVictuals(int id) {
        Victual victual = null;



        return victual;
    }
}
