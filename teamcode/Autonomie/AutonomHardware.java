package org.firstinspires.ftc;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class AutonomHardware extends LinearOpMode {

    //F-fata, S-spate, D-dreapta, S-stanga
    public DcMotor motorFS = null;
    public DcMotor motorFD = null;
    public DcMotor motorSS = null;
    public DcMotor motorSD = null;

    public DcMotor[] motoare = new DcMotor[4];

    public DcMotor motorBrat = null;

    public Servo ghearaStanga = null;
    public Servo ghearaDreapta = null;

    public BHI260IMU imu = null;
    HardwareMap hwMap = null;

    public final float standardSpeed = 0.3f;
    private int nrAutonomie = 1;

    public void init(HardwareMap ahwMap) {//init_loop ca sa se repete pana dai play

        hwMap = ahwMap;

        /*________________________Motoare Roti____________________________*/
        motorFS = hwMap.get(DcMotor.class, "motor FataStanga");
        motorFD = hwMap.get(DcMotor.class, "motor FataDreapta");
        motorSS = hwMap.get(DcMotor.class, "motor SpateStanga");
        motorSD = hwMap.get(DcMotor.class, "motor SpateDreapta");

        motoare = new DcMotor[]{motorFS, motorFD, motorSS, motorSD};

        motorFS.setDirection(DcMotor.Direction.FORWARD);
        motorFD.setDirection(DcMotor.Direction.REVERSE);
        motorSS.setDirection(DcMotor.Direction.FORWARD);
        motorSD.setDirection(DcMotor.Direction.REVERSE);

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        for(DcMotor motor : motoare)
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        for(DcMotor motor : motoare)
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frana();


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


        //____________________Senzori_____________________*/
        imu = hwMap.get(BHI260IMU.class, "imu");

        boolean prevPressed = false;

        //vezi daca isOpModeActive == true dupa init
        while(true) {
            if(prevPressed) {
                if (gamepad2.right_bumper)
                    nrAutonomie++;
                if (gamepad2.left_bumper)
                    nrAutonomie--;
            }
            if(gamepad2.left_bumper || gamepad2.right_bumper)
                prevPressed = true;

            telemetry.addLine(nrAutonomie + "");
            telemetry.update();
        }
    }

    public void frana() {//se opreste
        motorFS.setPower(0); motorFD.setPower(0);
        motorSS.setPower(0); motorSD.setPower(0);
        motorBrat.setPower(0);
    }

    public void moveForward() {//miscare fata spate
        motorFS.setPower(standardSpeed); motorFD.setPower(standardSpeed);
        motorSS.setPower(standardSpeed); motorSD.setPower(standardSpeed);
    }
    public void moveBack() {//miscare fata spate
        motorFS.setPower(-standardSpeed); motorFD.setPower(-standardSpeed);
        motorSS.setPower(-standardSpeed); motorSD.setPower(-standardSpeed);
    }
    public void strafeLeft() {//miscare laterala
        motorFS.setPower(-standardSpeed); motorFD.setPower(standardSpeed);
        motorSS.setPower(standardSpeed); motorSD.setPower(-standardSpeed);
    }
    public void strafeRight() {//miscare laterala
        motorFS.setPower(standardSpeed); motorFD.setPower(-standardSpeed);
        motorSS.setPower(-standardSpeed); motorSD.setPower(standardSpeed);
    }
    public void rotateLeft() {//rotire pe loc
        motorFS.setPower(-standardSpeed); motorFD.setPower(standardSpeed);
        motorSS.setPower(-standardSpeed); motorSD.setPower(standardSpeed);
    }
    public void rotateRight() {//rotire pe loc
        motorFS.setPower(standardSpeed); motorFD.setPower(-standardSpeed);
        motorSS.setPower(standardSpeed); motorSD.setPower(-standardSpeed);
    }

    //brat
    public void liftArm() {
        motorBrat.setPower(standardSpeed);
    }
    public void lowerArm() {
        motorBrat.setPower(-standardSpeed);
    }

    public void closeClaw() {
        ghearaStanga.setPosition(0);
        ghearaDreapta.setPosition(0);
    }
    public void openClaw() {
        ghearaDreapta.setPosition(0.25f);
        ghearaStanga.setPosition(0.25f);
    }



    //public void moveDiagonal(float x, String way) {//miscare pe diagonala
    //    if(way.equals("FD")) {//x = y
    //        motorFS.setPower(x); motorFD.setPower(0);
    //        motorSS.setPower(0); motorSD.setPower(x);
    //    }
    //    else {//x = -y
    //        motorFS.setPower(0);  motorFD.setPower(x);
    //        motorSS.setPower(x);  motorSD.setPower(0);
    //    }
    //}
//
    //public void moveDiagonal(float x, float y) {//se misca pe diagonala
    //    motorFS.setPower(x); motorFD.setPower(y);
    //    motorSS.setPower(y); motorSD.setPower(x);
    //}
//
//
//
    //public void doDrift(float x, String way) {
    //    if(way.equals("D")) {
    //        motorFS.setPower(x); motorFD.setPower(0);
    //        motorSS.setPower(x); motorSD.setPower(0);
    //    }
    //    else
    //        motorFS.setPower(0); motorFD.setPower(x);
    //        motorSS.setPower(0); motorSD.setPower(x);
    //}
//
    //public void doDrift2(float x, String way) {
    //    if(way.equals("F")) {
    //        motorFS.setPower(x); motorFD.setPower(-x);
    //        motorSS.setPower(0); motorSD.setPower(0);
    //    }
    //    else{
    //        motorFS.setPower(-x); motorFD.setPower(x);
    //        motorSS.setPower(0); motorSD.setPower(0);
    //    }
    //}
//
    //public void motorsPowerTelemetry(Telemetry telemetrie) {//afiseaza puterea motoarelor
    //    telemetrie.addLine("FD: " + motorFD.getPower());
    //    telemetrie.addLine("FS: " + motorFS.getPower());
    //    telemetrie.addLine("SS: " + motorSS.getPower());
    //    telemetrie.addLine("SD: " + motorSD.getPower());
//
    //    telemetrie.addLine();
    //}
//
    //public void motorsPositionTelemetry(Telemetry telemetrie) {//afiseaza puterea motoarelor
    //    telemetrie.addLine("FD: " + motorFD.getCurrentPosition());
    //    telemetrie.addLine("FS: " + motorFS.getCurrentPosition());
    //    telemetrie.addLine("SS: " + motorSS.getCurrentPosition());
    //    telemetrie.addLine("SD: " + motorSD.getCurrentPosition());
//
    //    telemetrie.addLine();
    //}

    @Override
    public void runOpMode() throws InterruptedException {}
}