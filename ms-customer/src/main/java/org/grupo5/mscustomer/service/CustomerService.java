package org.grupo5.mscustomer.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.grupo5.mscustomer.domain.Customer;
import org.grupo5.mscustomer.domain.dtos.CustomerCreateDto;
import org.grupo5.mscustomer.domain.dtos.CustomerUpdateDto;
import org.grupo5.mscustomer.exceptions.ex.CustomerNotFoundException;
import org.grupo5.mscustomer.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Value("${aws.bucket.name}")
    private String bucketName;


    private final  AmazonS3 s3Client;

    private final CustomerRepository customerRepository;

    public Customer createCustomer(CustomerCreateDto customerCreateDto){
        String imgURL = null;

        if(customerCreateDto.getPhotoBase64() != null){
            imgURL = this.uploadImg(customerCreateDto.getPhotoBase64());
        }


        Customer customer = new Customer();
        customer.setCpf(customerCreateDto.getCpf());
        customer.setName(customerCreateDto.getName());
        customer.setBirthday(customerCreateDto.getBirthday());
        customer.setGender(customerCreateDto.getGender());
        customer.setUrl_photo(imgURL);
        customer.setPoints(0);
        customer.setEmail(customerCreateDto.getEmail());



        customerRepository.save(customer);

        return customer;
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findById(id).orElseThrow(
               () -> new CustomerNotFoundException("User not found")
       );
    }

    public void deleteCustomerById(Long id){
        var customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("User not found")
        );
        customerRepository.delete(customer);
    }

    public Customer updateCustomer(Long id, CustomerUpdateDto customerUpdateDto){
       var customerOld = customerRepository.findById(id).orElseThrow(
               () -> new CustomerNotFoundException("Customer not found")
       );
       BeanUtils.copyProperties(customerUpdateDto,customerOld);
        return customerRepository.save(customerOld);
    }



    public URI getLocation(Customer customer){
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("customers/{id}")
                .buildAndExpand(customer.getId())
                .toUri();
        return location;
    }





    //UPLOAD PHOTO

    public String uploadImg(String base64Image){
        String filename = UUID.randomUUID() + ".PNG";
        String filePath = "/tmp/" + filename;

        try{
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
            File file = bytesToFile(decodedBytes,filePath);
            s3Client.putObject(bucketName, filename,file );
            file.delete();
            return s3Client.getUrl(bucketName, filename).toString();
        } catch (Exception e){
            System.out.println("erro ao subir arquivo");
            System.out.println(e.getMessage());
            return "";
        }
    }

    private static File bytesToFile(byte[] bytes, String filePath) throws IOException {
        File file = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        }
        return file;
    }

}
