package dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class EmployeeDto {
    private String name;
    private String city;
    private String created;

    public EmployeeDto(String name, String city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public String toString() {
        return String.format("Employee info. Name - %s, City - %s, Created - %s.", name, city, created);
    }
}
