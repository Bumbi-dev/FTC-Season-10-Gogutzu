package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.teamcode.Constants;
import java.io.File;


public class RecordHardware extends AutonomousHardware {
    private String culoareAutonomie = "_blue";
    private boolean test = false;
    private boolean mov = false;
    private boolean backBoard = false;
    public float voltaj;

    public void init(HardwareMap hwMap) {
        boolean prevPressed = false;

        initul(hwMap);

        String path = "";
        while (!this.isStarted() && !this.isStopRequested()) {
            if(gamepad2.back)
                culoareAutonomie = "_blue";
            if(gamepad2.start)
                culoareAutonomie = "_red";
            if(gamepad2.ps)
                culoareAutonomie = "_mov";
            if(gamepad2.dpad_right)
                test = true;
            if(gamepad2.dpad_left)
                test = false;

            if(gamepad1.back)
                backBoard = false;
            if(gamepad1.start)
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

            if(mov)
                path += "_mov";
            if(backBoard)
                path += "_backboard";
            if(test)
                path += "_test";

            path += nrAutonomie;

            telemetry.addLine(path);
            telemetry.update();
        }
        fila = new File(path + ".txt");

        VoltageSensor myControlHubVoltageSensor;
        myControlHubVoltageSensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

        voltaj = (float) myControlHubVoltageSensor.getVoltage();

        ReadWriteFile.writeFile(new File(path + "_voltaj.txt"), voltaj + "");

        path += ".txt";
    }
    @Override
    public void runOpMode() throws InterruptedException{}
}