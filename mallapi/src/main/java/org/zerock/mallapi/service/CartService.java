package org.zerock.mallapi.service;

import java.util.List;

import org.zerock.mallapi.dto.CartItemDTO;
import org.zerock.mallapi.dto.CartItemListDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface CartService {
  public List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO);
  public List<CartItemListDTO> getCartItems(String email);
  public List<CartItemListDTO> remove(Long cino);  
}
