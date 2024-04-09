package com.mziuri;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/room")
public class RoomServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Chatroom> chatrooms = DatabaseService.getInstance().getAllChatrooms();
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(chatrooms));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int maxMembers = Integer.parseInt(request.getParameter("maxMembers"));

        Chatroom chatroom = new Chatroom(name, maxMembers);
        DatabaseService.getInstance().createChatroom(chatroom);

        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}