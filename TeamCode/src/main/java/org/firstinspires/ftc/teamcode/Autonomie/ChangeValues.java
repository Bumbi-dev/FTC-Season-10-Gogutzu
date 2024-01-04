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
    File fila;
    String[] filePaths = new String[6];
    String[] partiRoti;

    @Override
    public void init() {
        for(int i = 1; i <= 3; i++) {
            filePaths[i - 1] = Constants.path + i + ".txt";
            filePaths[i + 2] = Constants.path + i + "_red.txt";
        }
    }
    @Override
    public void start() {}

    int i = 0;
    char prevDir = '0';

    @Override
    public void loop() {
        if(fila == null) {
            if(gamepad2.left_bumper && prevDir != 'l') {
                i--;
                if(i < 0)
                    i = filePaths.length - 1;
                prevDir = 'l';
            }

            if(gamepad2.right_bumper && prevDir != 'r') {
                i++;
                if(i > filePaths.length - 1)
                    i = 0;
                prevDir = 'r';
            }

            if(!gamepad2.left_bumper && !gamepad2.right_bumper)
                prevDir = '0';

            telemetry.addLine(filePaths[i]);
            telemetry.update();

            if(gamepad2.start) {
                fila = new File(filePaths[i]);
                String roti;
                roti = ReadWriteFile.readFile(fila);
                telemetry.addLine(roti);
                telemetry.update();
                partiRoti = roti.trim().split("\\s+");

                prevDir = '0';
                //nextVariables();
            }
            return;
        }

        if(gamepad2.left_bumper && prevDir != 'l') {
            prevVariables();
            prevDir = 'l';
        }
        if(gamepad2.right_bumper && prevDir != 'r') {
            nextVariables();
            prevDir = 'r';
        }
        if(!gamepad2.left_bumper && !gamepad2.right_bumper)
            prevDir = '0';

        time += gamepad2.right_stick_y / 100;
        time += gamepad2.left_stick_y / 1000;

        partiRoti[nrRoti - 1] = String.valueOf(time);

        telemetry.addLine(direction + '\n' + time);

        if(gamepad2.ps) {
            ReadWriteFile.writeFile(fila, rezultat());
            telemetry.addLine("gata");
            telemetry.update();
            requestOpModeStop();
        }
    }

    private String rezultat() {
        String ata = "";
        for (String s : partiRoti)
            ata += s + ' ';

        return ata;
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