package com.jmt.board.controllers;

import com.jmt.board.entities.BoardData;
import lombok.Data;

import java.util.List;

@Data
public class RequestAdminList {
    private List<BoardData> items;
}
