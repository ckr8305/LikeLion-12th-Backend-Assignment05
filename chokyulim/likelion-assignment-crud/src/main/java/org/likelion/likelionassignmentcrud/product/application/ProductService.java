package org.likelion.likelionassignmentcrud.product.application;

import org.likelion.likelionassignmentcrud.product.api.dto.request.ProductSaveReqDto;
import org.likelion.likelionassignmentcrud.product.api.dto.response.ProductInfoResDto;
import org.likelion.likelionassignmentcrud.product.api.dto.response.ProductListResDto;
import org.likelion.likelionassignmentcrud.product.domain.Product;
import org.likelion.likelionassignmentcrud.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) { // 어노테이션
        this.productRepository = productRepository;
    }

    // 사용자 저장
    @Transactional
    public void productSave(ProductSaveReqDto productSaveReqDto) { // 포장 되어있는 걸 까서 저장(데베에)
        // 이름, 나이, 파트
        Product product = Product.builder()
                .name(productSaveReqDto.name())
                .price(productSaveReqDto.price())
                .part(productSaveReqDto.part())
                .build(); // 포장 product로 넣어줌

        productRepository.save(product);
    }

    // 사용자 조회(모두 or 특정 값을 가진 친구들: Dto 두 개 만든 이유(재사용))
    // Dto 따로 해주는 이유 - 안전(데이터 바뀌지 않게)

    // 모두
    public ProductListResDto productFindAll() {
        List<Product> products = productRepository.findAll();

        List<ProductInfoResDto> productInfoResDtos =  products.stream()
                .map(ProductInfoResDto::from)
                .toList(); // 멤버 하나씩 돌면서 리스트 감싸줌..? 그걸 담음,,???

        return ProductListResDto.from(productInfoResDtos); // 사용자가 Dto에 담겨서 전체 조회
    }

}
