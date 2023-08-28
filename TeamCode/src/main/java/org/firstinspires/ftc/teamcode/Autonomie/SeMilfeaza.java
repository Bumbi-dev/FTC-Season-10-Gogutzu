package org.firstinspires.ftc.teamcode.Autonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ReadWriteFile;


import java.io.File;
import java.util.Arrays;

@TeleOp(name="Milfeaza", group="Bursuc")
public class SeMilfeaza extends AutonomHardware {//inregistreaza pozitiile date de dpad si le scrie in fisierul movement

    File movement = new File("/storage/emulated/0/FIRST/movement");
    String data = "";
    String []numeDirectii = {"sus", "jos", "dreapta", "stanga"};
    String current;

    @Override
    public void runOpMode() {
        init(hardwareMap);

        String prev = "0";

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.ps)
                return;

            current = "0";

            getDirection();


            switch (current) {
                case "sus":
                    moveStraight(standardSpeed);
                    break;

                case "jos":
                    moveStraight(-standardSpeed);
                    break;

                case "dreapta":
                    moveStrafe(standardSpeed);
                    break;

                case "stanga":
                    moveStrafe(-standardSpeed);
                    break;

                case "0" :
                    frana();
                    break;

                default://in caz ca ceva nu merge bine se opreste tot
                    return;
            }

            if(!prev.equals(current) && !prev.equals("0"))
                savePosition();

            telemetry.addLine(current);
            telemetry.addLine(motorFS.getCurrentPosition() + " " +  motorFD.getCurrentPosition() + '\n'+
                    motorSS.getCurrentPosition() + " " +  motorSD.getCurrentPosition());
            telemetry.update();

            prev = current;

        }

        ReadWriteFile.writeFile(movement, data);
    }

    private void savePosition () {//saves the position of all motors
         data += motorFS.getCurrentPosition() + " " +  motorFD.getCurrentPosition() + " \n"+
                 motorSS.getCurrentPosition() + " " +  motorSD.getCurrentPosition() + " \n\n";

    }

    private void getDirection() {//gets the direction determined by the dpad
        boolean []directii = {gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left};

        for (int i = 0; i < directii.length; i++) {
            if (directii[i]) {
                current = numeDirectii[i];
                break;
            }
        }
    }
}