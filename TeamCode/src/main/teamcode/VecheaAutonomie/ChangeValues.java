package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.teamcode.Constants;

import java.io.File;

//MODIFICI AUTONOMIA SI APESI PS CA SA SALVEZI CE AI SCHIMBAT

@Autonomous(name="Eu am MODIFICAT autonomia", group="modify")
public class ChangeValues extends LinearOpMode {
    int nrRoti = 0;
    double time;
    String direction;
    File fila;
    String[] partiRoti;
    char prevDir = '0';


    @Override
    public void runOpMode() {
        boolean prevPressed = false;
        boolean test = false;
        boolean backBoard = false;
        int nrAutonomie = 2;

        String culoareAutonomie = "_blue";

        String path = "";
        while (!this.isStarted() && !this.isStopRequested()) {
            if (gamepad2.back)
                culoareAutonomie = "_blue";
            if (gamepad2.start)
                culoareAutonomie = "_red";
            if (gamepad2.ps)
                culoareAutonomie = "_mov";
            if (gamepad2.dpad_right)
                test = true;
            if (gamepad2.dpad_left)
                test = false;

            if (gamepad1.back)
                backBoard = false;
            if (gamepad1.start)
                backBoard = true;

            if (gamepad2.right_bumper && !prevPressed)
                nrAutonomie++;
            if (gamepad2.left_bumper && !prevPressed)
                nrAutonomie--;

            if (nrAutonomie > 3)
                nrAutonomie = 3;
            if (nrAutonomie < 1)
                nrAutonomie = 1;

            prevPressed = gamepad2.left_bumper || gamepad2.right_bumper;

            path = Constants.path + culoareAutonomie;

            if (test)
                path += "_test";
            if (backBoard)
                path += "_backboard";

            path += nrAutonomie;

            telemetry.addLine(path);
            telemetry.update();
        }
        fila = new File(path + ".txt");
        String roti = ReadWriteFile.readFile(fila);

        partiRoti = roti.trim().split("\\s+");

        prevDir = '0';
        nextVariables();


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

            time -= gamepad2.right_stick_y / 1000;
            time -= gamepad2.left_stick_y / 10000;

            partiRoti[nrRoti + 1] = String.valueOf(time);

            telemetry.addLine("Direction: " + direction);
            telemetry.addLine("Time: " +  time);
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
        if(nrRoti >= partiRoti.length - 2)
            return;
        nrRoti += 2;

        direction = partiRoti[nrRoti];
        time = Double.parseDouble(partiRoti[nrRoti + 1]);
    }

    private void prevVariables() {
        if(nrRoti <= 0)
            return;
        nrRoti -= 2;

        direction = partiRoti[nrRoti];
        time = Double.parseDouble(partiRoti[nrRoti + 1]);
    }
}