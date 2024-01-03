package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.teamcode.Constants;

import java.io.File;
import java.util.Arrays;

@Autonomous(name="Eu am MODIFICAT autonomia", group="modify")
public class ChangeValues extends OpMode {
    int nrRoti = 0;
    double time;
    String direction;
    String path = Constants.folder + "autonomie";
    File fila;
    String[] filePaths;
    String[] partiRoti;

    @Override
    public void init() {


    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        int i = 0;
        char prevDir = '0';

        while(fila == null) {
            if(gamepad2.left_bumper && prevDir != 'l') {
                i--;
                if(i < 0)
                    i = filePaths.length;
                prevDir = 'l';
            }

            if(gamepad2.right_bumper && prevDir != 'r') {
                i++;
                if(i > filePaths.length + 1)
                    i = 0;
                prevDir = 'r';
            }
            telemetry.addLine(filePaths[i]);
            telemetry.update();

            if(gamepad2.start) {
                path += filePaths[i] + ".txt";
                fila = new File(path);
                String roti;
                roti = ReadWriteFile.readFile(fila);
                partiRoti = roti.trim().split("\\s+");

                nextVariables();
            }
            continue;
        }


        telemetry.addLine(direction + " " + time);




        if(gamepad2.ps)
            ReadWriteFile.writeFile(fila, rezultat());
    }

    private void rezultat () {
        //convert parti roit
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