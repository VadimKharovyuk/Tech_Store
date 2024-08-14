package com.example.webservice.controller;

import com.example.webservice.dto.DeliveryDTO;
import com.example.webservice.dto.DeliveryRequest;
import com.example.webservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/create")
    public String showCreateDeliveryForm(Model model) {
        // Создаем пустой объект DeliveryRequest для формы
        model.addAttribute("deliveryRequest", new DeliveryRequest());
        return "deliveries/create-delivery";
    }

    @PostMapping("/deliveries")
    public String createDelivery(@ModelAttribute DeliveryRequest request) {
        // Создаем доставку и перенаправляем на страницу с доставками
        orderService.createDelivery(request);
        return "redirect:/orders/deliveries";
    }

    @GetMapping("/deliveries")
    public String listDeliveries(Model model) {
        List<DeliveryDTO> deliveries = orderService.getAllDeliveries();
        model.addAttribute("deliveries", deliveries);
        return "deliveries/list-deliveries";
    }
}
