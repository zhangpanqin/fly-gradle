package com.mflyyou.application;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.javamoney.moneta.Money;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@RestController
public class DemoController {
    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }

    @PostMapping("/dc-3512")
    public DcPayload dc3512(@RequestBody DcPayload payload) {
        System.out.println(DcPayload.builder().build());
        return payload;
    }

    @Getter
    @Builder
    @Jacksonized
    public static class DcPayload implements Serializable{
        @Serial
        private static final long serialVersionUID = -8859377635691053219L;
        private List<PayoutFlowItem> payoutFlows;

        @Getter
        @Builder
        @Jacksonized
        public static class PayoutFlowItem implements Serializable {
            @Serial
            private static final long serialVersionUID = 1L;

            private String settlementId;

            private String sourceAccountId;

            private String destinationAccountId;

            private Money amount;
        }
    }
}
