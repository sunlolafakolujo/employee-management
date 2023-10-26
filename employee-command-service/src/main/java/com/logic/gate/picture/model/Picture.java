package com.logic.gate.picture.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Picture implements Serializable {
    @Id
    @SequenceGenerator(name = "picture_generator",
            sequenceName = "picture_seq", allocationSize = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "picture_generator")
    private Long id;
    private String imageType;
    private String imageName;

    @Column(length = 10000)
    private byte[] picBytes;

    public Picture(String imageType, String imageName, byte[] picBytes) {
        this.imageType = imageType;
        this.imageName = imageName;
        this.picBytes = picBytes;
    }
}
