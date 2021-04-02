package com.example.managementsystem.FragementMenu;

public class PdfBillUpload {
    private String filename, fileurl;

    public PdfBillUpload(String filename, String fileurl) {
        this.filename = filename;
        this.fileurl = fileurl;
    }
    public PdfBillUpload(){

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
