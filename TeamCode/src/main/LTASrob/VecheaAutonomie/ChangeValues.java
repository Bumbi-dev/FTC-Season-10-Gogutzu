package org.firstinspires.ftc.LTASrob.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.LTASrob.Constants;

import java.io.File;

@Autonomous(name="Eu am MODIFICAT autonomia", group="modify")
public class ChangeValues extends LinearOpMode {
    int nrRoti = 0;
    double time;
    String direction;
    File fila;
    String[] filePaths = new String[6];
    String[] partiRoti;
    int i = 0;
    char prevDir = '0';


    @Override
    public void runOpMode() {
        for(int i = 0; i < 3; i++) {
            filePaths[i] = Constants.path + (i + 1) + "_blue.txt";
            filePaths[i + 3] = Constants.path + (i + 1) + "_red.txt";
        }

        boolean prevPressed = false;
        boolean isRed = false;
        int nrAutonomie = 2;

        while (!this.isStarted() && !this.isStopRequested()) {
            if(gamepad2.back)
                isRed = false;
            if(gamepad2.start)
                isRed = true;

            if (gamepad2.right_bumper && !prevPressed)
                nrAutonomie++;
            if (gamepad2.left_bumper && !prevPressed)
                nrAutonomie--;

            if (nrAutonomie > 3)
                nrAutonomie = 3;
            if (nrAutonomie < 1)
                nrAutonomie = 1;

            prevPressed = gamepad2.left_bumper || gamepad2.right_bumper;

            if(isRed)
                i = 3;
            else
                i = 0;

            telemetry.addLine(filePaths[nrAutonomie + i - 1]);
            telemetry.update();
        }

        fila = new File(filePaths[i]);
        String roti;
        roti = ReadWriteFile.readFile(fila);
        telemetry.addLine(roti);
        telemetry.update();
        partiRoti = roti.trim().split("\\s+");

        prevDir = '0';
        //nextVariables();

        while(opModeIsActive()) {
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

            partiRoti[nrRoti] = String.valueOf(time);

            telemetry.addLine(direction + '\n' + time);
            telemetry.update();

            if(gamepad2.ps) {
                ReadWriteFile.writeFile(fila, rezultat());
                telemetry.addLine("gata");
                telemetry.update();
                requestOpModeStop();
            }
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