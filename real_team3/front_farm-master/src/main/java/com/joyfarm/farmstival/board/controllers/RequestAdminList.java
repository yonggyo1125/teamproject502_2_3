package com.joyfarm.farmstival.board.controllers;

import com.joyfarm.farmstival.board.entities.BoardData;
import lombok.Data;

import java.util.List;

@Data
public class RequestAdminList {
    private List<BoardData> items;

}
