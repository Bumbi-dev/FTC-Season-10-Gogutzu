package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.teamcode.Constants;

import java.io.File;

@Autonomous(name="Eu am facut autonomia (play)", group="Bubu")
public class Autonomie_Dezvoltata extends AutonomHardware {

    int nrRoti = 0;
    double time;
    String direction;

    ElapsedTime runtime;
    File fila = new File(Constants.folder + "autonomie");
    String roti = ReadWriteFile.readFile(fila);

    String[] partiRoti = roti.trim().split("\\s+");

    @Override
    public void runOpMode() {
        runtime = new ElapsedTime();

        init(hardwareMap);

        waitForStart();

        runtime.reset();
        while (opModeIsActive()) {
            nextVariables();
            proceed();
        }
    }
    private void nextVariables(){
        direction = partiRoti[nrRoti++];
        time = Double.parseDouble(partiRoti[nrRoti++]); // replace this with the first float in the file

        telemetry.addLine(time + " " + direction);
        telemetry.update();
    }
    private void proceed(){
        runtime.reset();
        chooseDirection();

        while(runtime.seconds() < time && opModeIsActive())
            try {
                wait(1);
            }catch (Exception ignored){}
    }



    private void chooseDirection() {
        switch (direction) {
            case "f":
                moveForward();
                break;
            case "b":
                moveBack();
                break;
            case "l":
                strafeLeft();
                break;
            case "r":
                strafeRight();
                break;
            case "tl":
                rotateLeft();
                break;
            case "tr":
                rotateRight();
                break;
            default:
                frana();
                break;
        }
    }
}