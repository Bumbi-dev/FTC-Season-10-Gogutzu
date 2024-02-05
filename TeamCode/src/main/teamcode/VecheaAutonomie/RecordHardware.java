package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.teamcode.Constants;
import java.io.File;


public class RecordHardware extends AutonomousHardware {
    private String culoareAutonomie = "_blue";
    private boolean test = false;
    private boolean mov = false;
    private boolean backBoard = false;
    private final float standardSpeed = Constants.standardSpeed;
    public float voltaj;

    public void init(HardwareMap hwMap) {
        boolean prevPressed = false;

        motorFS = hwMap.get(DcMotor.class, "motor FataStanga");
        motorFD = hwMap.get(DcMotor.class, "motor FataDreapta");
        motorSS = hwMap.get(DcMotor.class, "motor SpateStanga");
        motorSD = hwMap.get(DcMotor.class, "motor SpateDreapta");

        DcMotor[] motoare = new DcMotor[]{motorFS, motorFD, motorSS, motorSD};

        motorFS.setDirection(DcMotor.Direction.FORWARD);
        motorFD.setDirection(DcMotor.Direction.REVERSE);
        motorSS.setDirection(DcMotor.Direction.FORWARD);
        motorSD.setDirection(DcMotor.Direction.REVERSE);

        for (DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        for (DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        for (DcMotor motor : motoare)
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*________________________Brat____________________________*/
        motorBrat = hwMap.get(DcMotor.class, "motor Brat");

        motorBrat.setDirection(DcMotor.Direction.FORWARD);
        motorBrat.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBrat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBrat.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ghearaStanga = hwMap.get(Servo.class, "gheara Stanga");
        ghearaDreapta = hwMap.get(Servo.class, "gheara Dreapta");

        ghearaStanga.setDirection(Servo.Direction.FORWARD);
        ghearaDreapta.setDirection(Servo.Direction.REVERSE);

        frana();

        capcana = hwMap.get(Servo.class, "capcana");
        capcana.setPosition(0.374);

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
            if(test)
                path += "_test";
            if(backBoard)
                path += "_backboard";

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