package entities;

import lombok.*;

import java.util.List;

@Data
@Builder
public class Page {
    private String _id;
    private Boolean isTest;
    private String title;
    private Integer __v;
    private List<Page> features;
    private String status;
}
