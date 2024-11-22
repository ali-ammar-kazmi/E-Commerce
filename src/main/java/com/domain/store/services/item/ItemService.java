package com.domain.store.services.item;

import com.domain.store.exception.StoreException;
import com.domain.store.model.Item;
import com.domain.store.repository.ItemRepository;
import com.domain.store.services.cart.CartService;
import com.domain.store.services.product.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {

    private final ItemRepository itemRepository;
    private final CartService cartService;
    private final ProductService productService;

    @Override
    public Item addItemToCart(Long cartId, Long productId) {
        try {
            if (itemRepository.findByCartIdAndProductId(cartId, productId).isPresent()){
                Item item = itemRepository.findByCartIdAndProductId(cartId, productId).get();
                item.setQuantity(item.getQuantity()+1);
                item.setPrice();
                return itemRepository.save(item);
            }
            Item newItem = new Item();
            newItem.setCart(cartService.getCart(cartId));
            newItem.setProduct(productService.getProductById(productId));
            newItem.setPrice();
            return itemRepository.save(newItem);
        } catch (Exception e) {
            throw new StoreException(e.getMessage());
        }
    }

    @Override
    public Item getCartItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(()-> new StoreException("Item Not Found with id: " + id));
        return itemRepository.save(item);
    }

    @Override
    public Item updateQuantity(Long id, int quantity) {
        Item item = getCartItem(id);
        int updatedQuantity = item.getQuantity() + quantity;
        if ( updatedQuantity <= 0){
            deleteCartItem(id);
            return null;
        }
        item.setQuantity(updatedQuantity);
        item.setPrice();
        return itemRepository.save(item);
    }

    @Override
    public void deleteCartItem(Long id) {
        itemRepository.findById(id).ifPresentOrElse(itemRepository::delete, ()-> { throw new StoreException("CartItem Not Found with id: " + id);});
    }
}
