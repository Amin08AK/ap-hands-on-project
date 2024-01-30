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


    public void PersonInfoPrint(Personnel p){
        System.out.println("Name: " + p.getName());
        System.out.println("Age: " + p.getAge());
        System.out.println("ID: " + p.getID());
        System.out.println("Gender: " + p.getGender());
        System.out.println("Job: " + p.getJob());
        System.out.println("Responsibilities: " + p.getResponsibilities());
    }

}
