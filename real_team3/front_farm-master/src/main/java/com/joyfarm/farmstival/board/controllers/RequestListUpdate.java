package com.joyfarm.farmstival.board.controllers;

import com.joyfarm.farmstival.board.entities.BoardData;
import lombok.Data;

import java.util.List;

@Data
public class RequestListUpdate {
    private List<BoardData> items;
}
