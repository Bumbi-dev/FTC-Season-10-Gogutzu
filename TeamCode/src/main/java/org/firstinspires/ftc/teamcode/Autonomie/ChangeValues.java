package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import java.io.File;

@Autonomous(name="Eu am MODIFICAT autonomia", group="modify")
public class ChangeValues extends OpMode {

    int nrRoti = 0;
    double time;
    String direction;

    File fila;
    String roti;

    String[] filesPaths;
    String[] partiRoti;

    @Override
    public void init() {


    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        fila = new File(path);
        roti = ReadWriteFile.readFile(fila);
        partiRoti = roti.trim().split("\\s+");
    }

    private void nextVariables() {
        direction = partiRoti[nrRoti++];
        time = Double.parseDouble(partiRoti[nrRoti++]);
    }

    private void prevVariables() {
        direction = partiRoti[nrRoti--];
        time = Double.parseDouble(partiRoti[nrRoti--]);
    }
}