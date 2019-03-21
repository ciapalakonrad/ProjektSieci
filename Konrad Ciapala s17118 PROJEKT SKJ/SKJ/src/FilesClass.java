public class FilesClass {
    String fileName, message;

    //FilesClass constructor with 2 args
    public FilesClass(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }

    //writing comunicates in the console
    public String toString() {
        return this.fileName;
    }
}
