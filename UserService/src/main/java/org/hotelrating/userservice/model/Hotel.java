package org.hotelrating.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel implements Serializable {

    private  String id;
    private  String name;
    private  String location;
    private  String about;

}