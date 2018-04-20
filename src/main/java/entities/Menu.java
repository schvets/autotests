package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
public class Menu {
    @Singular private List<String> levels;
    String pageUrl;
    String title;
}
