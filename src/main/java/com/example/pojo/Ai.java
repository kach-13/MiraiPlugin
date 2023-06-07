package bot.pojo;

public class Ai {
    private Addition addition;
    private Integer channel = 0;
    private String content;
    private Integer height = 512;
    private Integer model = 1;
    private Integer multiply = 1;
    private String ref_img = "";
    private String use_symmetry = "0";
    private Integer width = 512;

    public AiData getData() {
        return data;
    }

    public void setData(AiData data) {
        this.data = data;
    }

    private AiData data;
    public Addition getAddition() {
        return addition;
    }

    public void setAddition(Addition addition) {
        this.addition = addition;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Integer getMultiply() {
        return multiply;
    }

    public void setMultiply(Integer multiply) {
        this.multiply = multiply;
    }

    public String getRef_img() {
        return ref_img;
    }

    public void setRef_img(String ref_img) {
        this.ref_img = ref_img;
    }

    public String getUse_symmetry() {
        return use_symmetry;
    }

    public void setUse_symmetry(String use_symmetry) {
        this.use_symmetry = use_symmetry;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}