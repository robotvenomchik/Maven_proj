package org.example.Homework38;


import org.example.Homework38.service.CartService;
import org.example.Homework38.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class CartConsoleApp implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final CartService cartService;

    public CartConsoleApp(ProductRepository productRepository, CartService cartService) {
        this.productRepository = productRepository;
        this.cartService = cartService;
    }


    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Показати всі товари");
            System.out.println("2. Додати товар у кошик");
            System.out.println("3. Видалити товар з кошика");
            System.out.println("4. Показати кошик");
            System.out.println("5. Підрахувати загальну вартість");
            System.out.println("6. Вийти");

            System.out.print("Виберіть опцію: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    productRepository.findAll().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Введіть ID товару: ");
                    int addId = scanner.nextInt();
                    if (cartService.addProductById(addId)) {
                        System.out.println("Товар додано!");
                    } else {
                        System.out.println("Товар не знайдено");
                    }
                    break;
                case 3:
                    System.out.print("Введіть ID товару: ");
                    int removeId = scanner.nextInt();
                    if (cartService.removeProductById(removeId)) {
                        System.out.println("Товар видалено!");
                    } else {
                        System.out.println("Товар не знайдено в кошику");
                    }
                    break;
                case 4:
                    System.out.println("Ваш кошик:");
                    cartService.viewCart().forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("Загальна вартість кошика: " + cartService.getTotalPrice() + " грн");
                    break;
                case 6:
                    System.out.println("Вихід...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Невірна опція, спробуйте ще раз");
            }
        }
    }
}
