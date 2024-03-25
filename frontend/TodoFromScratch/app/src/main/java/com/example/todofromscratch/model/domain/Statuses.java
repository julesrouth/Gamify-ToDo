
package com.example.todofromscratch.model.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Statuses {
    private int rage;
    private int poison;
    private int burn;
    private int frozen;
    private int frightened;
    private int confused;
    private int paralyzed;
    private List<String[]> clear;

    public Statuses() {
        rage = 0;
        poison = 0;
        burn = 0;
        frozen = 0;
        frightened = 0;
        confused = 0;
        paralyzed = 0;
        clear = new ArrayList<>();
    }

    public void clearStatuses() {
        rage = 0;
        poison = 0;
        burn = 0;
        frozen = 0;
        frightened = 0;
        confused = 0;
        paralyzed = 0;
    }

    public void addStatus(String status, int length) {
        setStatus(status, 1);
        clear.add(new String[]{status, Integer.toString(length)});
    }

    public void setStatus(String status, int value) {
        switch (status) {
            case "rage":
                rage = value;
                break;
            case "poison":
                poison = value;
                break;
            case "burn":
                burn = value;
                break;
            case "frozen":
                frozen = value;
                break;
            case "frightened":
                frightened = value;
                break;
            case "confused":
                confused = value;
                break;
            case "paralyzed":
                paralyzed = value;
                break;
            default:
                System.out.println("Invalid status");
        }
    }

    public int calculateAttack(int attack) {
        if (rage != 0) {
            attack = (int) Math.ceil(attack * 1.3);
        }
        if (frightened != 0) {
            attack = (int) Math.ceil(attack * 0.75);
        }
        return attack;
    }

    public List<String> checkStatus(int time) {
        List<String> tempList = new ArrayList<>();
        Iterator<String[]> iterator = clear.iterator();

        while (iterator.hasNext()) {
            String[] i = iterator.next();
            if (Integer.parseInt(i[1]) <= time) {
                iterator.remove();
                setStatus(i[0], 0);
                tempList.add("Cleared: " + i[0]);
            }
        }
        return tempList;
    }

    public boolean isRage() {
        return rage!=0;
    }

    public boolean isFrightened() {
        return frightened!=0;
    }
    public boolean isConfused() {
        return confused!=0;
    }
    public static void main(String[] args) {
        Statuses status = new Statuses();
        status.addStatus("rage", 10);
        status.checkStatus(5);
        System.out.print(status.checkStatus(15));
    }
}
