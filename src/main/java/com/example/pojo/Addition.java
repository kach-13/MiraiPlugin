package bot.pojo;

public class Addition {
    private Integer cfg_scale = 7;
    private Integer fill_prompt = 0;
    private String negative_prompt = "((bad mouth)),((opened mouth)),((disfigured)),((bad art)),((deformed)),((extra limbs)),((close up)),((b&w)),wierd colors,blurry,(((duplicate))),((morbid)),((mutilated)),[out of frame],extra fingers,mutated hands,((poorly drawn hands)),((poorly drawn face)),(((mutation))),(((deformed))),((ugly)),(((bad proportions))),cloned face,(((disfigured))),(bad anatomy),(malformed limbs),((missing arms)),((missing legs)),(((extra arms))),mutated hands,(((long neck)))";
    private Double strength = 0.7;

    public Integer getCfg_scale() {
        return cfg_scale;
    }

    public void setCfg_scale(Integer cfg_scale) {
        this.cfg_scale = cfg_scale;
    }

    public Integer getFill_prompt() {
        return fill_prompt;
    }

    public void setFill_prompt(Integer fill_prompt) {
        this.fill_prompt = fill_prompt;
    }

    public String getNegative_prompt() {
        return negative_prompt;
    }

    public void setNegative_prompt(String negative_prompt) {
        this.negative_prompt = negative_prompt;
    }

    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }
}
