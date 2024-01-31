public class Personnel extends Person {
    private String job;
    private String responsibilities;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }


    public void personInfoPrint(){
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("ID: " + getID());
        System.out.println("Gender: " + getGender());
        System.out.println("Job: " + getJob());
        System.out.println("Responsibilities: " + getResponsibilities());
    }

}
