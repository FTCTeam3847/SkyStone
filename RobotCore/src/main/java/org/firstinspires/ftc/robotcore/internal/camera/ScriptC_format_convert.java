package org.firstinspires.ftc.robotcore.internal.camera;

import android.content.res.Resources;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.Script.FieldID;
import android.renderscript.Script.KernelID;
import android.renderscript.Script.LaunchOptions;
import android.renderscript.ScriptC;

public class ScriptC_format_convert extends ScriptC {
    private static final String __rs_resource_name = "format_convert";
    private static final int mExportForEachIdx_yuv2_to_argb8888 = 1;
    private static final int mExportVarIdx_inputAllocation = 0;
    private static final int mExportVarIdx_outputHeight = 2;
    private static final int mExportVarIdx_outputWidth = 1;
    private Element __ALLOCATION;
    private Element __I32;
    private Element __U8_4;
    private FieldPacker __rs_fp_ALLOCATION;
    private FieldPacker __rs_fp_I32;
    private Allocation mExportVar_inputAllocation;
    private int mExportVar_outputHeight;
    private int mExportVar_outputWidth;

    public ScriptC_format_convert(RenderScript renderScript) {
        this(renderScript, renderScript.getApplicationContext().getResources(), renderScript.getApplicationContext().getResources().getIdentifier(__rs_resource_name, "raw", renderScript.getApplicationContext().getPackageName()));
    }

    public ScriptC_format_convert(RenderScript renderScript, Resources resources, int i) {
        super(renderScript, resources, i);
        this.__ALLOCATION = Element.ALLOCATION(renderScript);
        this.__I32 = Element.I32(renderScript);
        this.__U8_4 = Element.U8_4(renderScript);
    }

    public synchronized void set_inputAllocation(Allocation allocation) {
        setVar(0, allocation);
        this.mExportVar_inputAllocation = allocation;
    }

    public Allocation get_inputAllocation() {
        return this.mExportVar_inputAllocation;
    }

    public FieldID getFieldID_inputAllocation() {
        return createFieldID(0, null);
    }

    public synchronized void set_outputWidth(int i) {
        setVar(1, i);
        this.mExportVar_outputWidth = i;
    }

    public int get_outputWidth() {
        return this.mExportVar_outputWidth;
    }

    public FieldID getFieldID_outputWidth() {
        return createFieldID(1, null);
    }

    public synchronized void set_outputHeight(int i) {
        setVar(2, i);
        this.mExportVar_outputHeight = i;
    }

    public int get_outputHeight() {
        return this.mExportVar_outputHeight;
    }

    public FieldID getFieldID_outputHeight() {
        return createFieldID(2, null);
    }

    public KernelID getKernelID_yuv2_to_argb8888() {
        return createKernelID(1, 58, null, null);
    }

    public void forEach_yuv2_to_argb8888(Allocation allocation) {
        forEach_yuv2_to_argb8888(allocation, null);
    }

    public void forEach_yuv2_to_argb8888(Allocation allocation, LaunchOptions launchOptions) {
        if (allocation.getType().getElement().isCompatible(this.__U8_4)) {
            forEach(1, (Allocation) null, allocation, null, launchOptions);
            return;
        }
        throw new RSRuntimeException("Type mismatch with U8_4!");
    }
}
