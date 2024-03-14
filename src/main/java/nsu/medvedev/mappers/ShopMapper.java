package nsu.medvedev.mappers;

import nsu.medvedev.entities.Shop;
import nsu.medvedev.entities.ShopDTO;

public class ShopMapper {
    public static ShopDTO toShopDto(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setName(shop.getName());
        shopDTO.setId(shop.getId());
        shopDTO.setBooksDTO(shop.getBooks());
        return shopDTO;
    }

    public static Shop toShopEntity(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setBooks(shopDTO.getBooksDTO());
        shop.setId(shopDTO.getId());
        shop.setName(shopDTO.getName());
        return shop;
    }
}
