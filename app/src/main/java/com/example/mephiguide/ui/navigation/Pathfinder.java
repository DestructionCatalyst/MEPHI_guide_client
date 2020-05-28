package com.example.mephiguide.ui.navigation;

import com.example.mephiguide.MyLog;
import com.example.mephiguide.data_types.Dot;
import com.example.mephiguide.data_types.Way;

import java.util.ArrayList;
import java.util.Arrays;

public class Pathfinder {
    private ArrayList<Dot> dots;
    private double [] [] tab;
    private int dotNumber;

    public Pathfinder(ArrayList<Dot> dots, ArrayList<Way> ways){
        this.dots = dots;
        dotNumber = dots.size();
        tab = new double [dotNumber][dotNumber];

        for (int i = 0; i< dotNumber; i++){
            for (int j = 0; j< dotNumber; j++) {
                tab[i][j] = Double.MAX_VALUE/4-1;
            }

        }
        for (Way w:ways) {
            try{
                tab[w.getIdStart()-1][w.getIdEnd()-1] = w.getLen();
                tab[w.getIdEnd()-1][w.getIdStart()-1] = w.getLen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private int [] findaway(int from, int to){

        if ((from < 0)||(to < 0)){
            MyLog.i("Wrong start or end point!");
            return null;
        }
        else if (from == to){
            int [] arr = new int[2];
            arr[0] = from;
            arr[1] = to;
            return arr;
        }
        else{

            boolean [] finish = new boolean[dotNumber];
            double [] dist = new double[dotNumber];
            int [] prev = new int[dotNumber];
            int [] way = new int[dotNumber];

            Arrays.fill(dist, Double.MAX_VALUE/4-1); // устанаавливаем расстояние до всех вершин INF
            Arrays.fill(prev, 0);
            Arrays.fill(way, 0);
            dist[from] = 0; // для начальной вершины положим 0

            for (;;) {
                int v = -1;
                for (int nv = 0; nv < dotNumber; nv++) // перебираем вершины
                    if (!finish[nv] && dist[nv] < Double.MAX_VALUE/4-1 && (v == -1 || dist[v] > dist[nv]) ) // выбираем самую близкую непомеченную вершину
                        v = nv;
                if (v == -1) break; // ближайшая вершина не найдена
                finish[v] = true; // помечаем ее
                for (int nv = 0; nv < dotNumber; nv++)
                    if (!finish[nv] && tab[v][nv] < Double.MAX_VALUE/4-1 ) { // для всех непомеченных смежных
                        if (dist[nv]>dist[v] + tab[v][nv]) {
                            dist[nv] = dist[v] + tab[v][nv];// улучшаем оценку расстояния (релаксация)
                            prev[nv] = v;
                        }
                    }


            }
            int z = to, i = 0;
            do{//Восстанавливаем путь
                way[i] = prev[z];
                z = prev[z];
                i++;
            }
            while (z != from);
            int tmp;
            for (int j = 0; j<way.length/2; j++) {//Разворачиваем в правильном направлении
                tmp = way[j];
                way[j] = way[way.length-1-j];
                way[way.length-1-j]=tmp;
            }

            return way;
        }

    }

    int findId(String dotName){

        for (Dot d : dots) {
            if ((d.getName() != null) && (d.getName().equals(dotName))) {
                return d.getId();
            }
        }
        MyLog.w("Point not found!");
        return 0;
    }

    float[] getPath(int from, int to){

        ArrayList points = new ArrayList();

        int[] way = findaway(from - 1, to - 1);

        if (way != null) {
            if (from == to) {//Никуда не идём
                points.add(dots.get(from - 1).getX() - 3);
                points.add(dots.get(from - 1).getY() - 3);
                points.add(dots.get(from - 1).getX() + 3);
                points.add(dots.get(from - 1).getY() - 3);
                points.add(dots.get(from - 1).getX() + 3);
                points.add(dots.get(from - 1).getY() + 3);
                points.add(dots.get(from - 1).getX() - 3);
                points.add(dots.get(from - 1).getY() + 3);

            } else {
                if (from - 1 == 0) {//Если стартуем с проходной, обраб. отдельно

                    points.add(dots.get(from - 1).getX());
                    points.add(dots.get(from - 1).getY());
                }
                for (int j = 0; j < way.length; j++) {//Проходим по пути
                    if (way[j] > 0) {

                        points.add(dots.get(way[j]).getX());
                        points.add(dots.get(way[j]).getY());
                    }
                }
                points.add(dots.get(to - 1).getX());//Добавляем последнюю точку
                points.add(dots.get(to - 1).getY());


            }
            float[] floatArray = new float[points.size()];
            for (int index = 0; index < points.size(); index++) {
                floatArray[index] = (int) points.get(index);
            }
            return floatArray;
        } else {
            return null;
        }
    }

}
