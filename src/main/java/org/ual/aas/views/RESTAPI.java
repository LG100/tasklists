package org.ual.aas.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.json.*;

import org.ual.aas.controllers.TaskListController;

@WebServlet(name = "tasklists", urlPatterns = {"/lists", "/tasks"})
public class RESTAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	TaskListController controller = new TaskListController();
        String uri = req.getRequestURI();
        String[] splits = uri.split("/");
        
        if(splits.length == 3) { // GET /lists/
        	
        }
        else if(splits.length == 4) { // GET /lists/<list_id>/
        	// ["", "tasklists", "lists", list_id]
        	String listId = splits[3];
        }
    	else if(splits.length == 5) { // GET /lists/<list_id>/<task_id>/
    		// ["", "tasklists", "lists", list_id, task_id]
    		//String listId = splits[3];
    		String taskId = splits[4];
    		String listIds = splits[5];
    	}
    	else {
    		// 404
    	}
    	
        
        // Teste de resposta JSON simples
    	resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.println("{\"method\":\"GET\","
        		+ "\"value\":0,"
        		+ " \"uri\":"+uri+","
        		+ " \"splits_length\":"+splits.length+","
        		+ " \"first_split\":"+splits[1]+","
				+ " \"second_split\":"+ splits[2] + ""
        				+ "}");
        writer.close();
        controller.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("application/json");
    	TaskListController controller = new TaskListController();
    	String uri = req.getRequestURI();
    	String[] splits = uri.split("/");
        
        if(splits.length == 4) { // GET /lists/<list_id>/
        	// ["", "tasklists", "lists", list_id]
        	String listId = splits[3];
        }
    	else if(splits.length == 5) { // GET /lists/<list_id>/<task_id>/
    		// ["", "tasklists", "lists", list_id, task_id]
    		String listId = splits[3];
    		String taskId = splits[4];
    	}
    	else {
    		// 404
    	}
    	// Ler o nome da nova lista
    	// Ler conteúdo do request
    	BufferedReader reader = req.getReader();
    	String name = "" ;
		reader.readLine(); 	    	
    	/*
    	try {
    		StringBuffer s = new StringBuffer();
            while ((name = req.getReader().readLine()) != null) {
                s.append(name);
            }
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	*/
		
    	// 1- Trabalhar diretamente em Strings
    	// 2- javax.json
    	// 3- gson
		
    	
    	// Criar a lista no controlador
    	String taskListId = controller.createTaskList(name);
    			
    	// Responder (com o id da nova lista)
    	
        PrintWriter writer = resp.getWriter();
        writer.println("{\"id\":" + taskListId + " \"name\":" + name + "}" );
        writer.close();
        controller.close();
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("application/json");
    	TaskListController controller = new TaskListController();
    	
    	// TODO: ler o nome da nova lista
    	// Ler conteúdo do request
    	BufferedReader reader = req.getReader();
    	
    	//trabalhar o id das tabelas a alterar
    	
    	// Responder com a alteração 
        PrintWriter writer = resp.getWriter();
        //writer.println("{\"id\":" + taskListId + " \"name\":" + name + "}" );
        writer.close();
        controller.close();
     }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("application/json");
    	TaskListController controller = new TaskListController();
    	String uri = req.getRequestURI();
    	String[] splits = uri.split("/");
        
    	// TODO: trabalhar as variaves do JSON
    	// Ler conteúdo do request
    	String Id = req.getParameter("id");
    	String IdList = req.getParameter("idlist");
    	 
    	// trabalhar o id da tasklist ou task     	
    	
        if(splits[2] == "lists") { // GET /lists/<list_id>/
        	// ["", "tasklists", "lists", list_id]
        	controller.deleteTaskList(IdList);
        }
    	else if(splits[2] == "tasks") { // GET /lists/<list_id>/<task_id>/
    		// ["", "tasklists", "lists", list_id, task_id]
    		controller.deleteTask(IdList, Id);
    	}
    	else {
    		// 404
    	}

    	
    	// Responder com a a tarefa eliminada
    	PrintWriter writer = resp.getWriter();
        writer.println("{ dados eliminados : "
        		+ "\"id\":" + Id
        		+ "\"idList\":" + IdList 
        		+ "}" );
        writer.close();
        controller.close();
    }    
}
