package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ReadWriteFile;

import java.io.File;

@Disabled
@Autonomous(name="Eu am facut autonomia-ALBASTRU! (play)", group="play")
public class Autonomie_Dezvoltata_Blue extends AutonomHardware {

    int nrRoti = 0;
    double time;
    String direction;

    File fila;
    String roti;

    String[] partiRoti;

    @Override
    public void runOpMode() {
        isMatch = true;
        init(hardwareMap);

        path += nrAutonomie + ".txt";
        fila = new File(path);
        roti = ReadWriteFile.readFile(fila);
        partiRoti = roti.trim().split("\\s+");

        runtime.reset();

        while (opModeIsActive()) {
            nextVariables();
            proceed();
        }
    }

    private void nextVariables() {
        direction = partiRoti[nrRoti++];
        time = Double.parseDouble(partiRoti[nrRoti++]);
    }
    private void proceed(){
        runtime.reset();
        chooseDirection();

        while(runtime.seconds() < time)
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
            case "u"://up
                liftArm();
                break;
            case "d"://down
                lowerArm();
                break;
            case "g"://grab
                closeClaw();
                break;
            case "dr"://drop
                openClaw();
                break;
            default:
                frana();
                break;
        }
    }
}