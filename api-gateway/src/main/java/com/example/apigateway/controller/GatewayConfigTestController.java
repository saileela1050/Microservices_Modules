//package com.example.apigateway.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class GatewayConfigTestController {
//
//    @Value("${gateway.message}")
//    public String gatewayMessage;
//
//
//    @GetMapping("/gateway-config-test")
//    public String gatewayConfigTest(){
//        return gatewayMessage;
//    }
//}
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//public UpiCCMandateHist getMandateDetails(ReqMandate reqMandate) {
//    LOGGER.info("starting getMandateDetails ");
//    try {
//        UpiCCMandateHist upiMandate = new UpiCCMandateHist();
//        LOGGER.info("getMandateDetails: hist object created");
//        upiMandate.setOrgId(PropertyConstants.ORG_ID);
//        upiMandate.setMsgId(reqMandate.getHead().getMsgId());
//        upiMandate.setTxnId(reqMandate.getTxn().getId());
//        if (Constants.CREATE.equals(reqMandate.getTxn().getType().value())) {
//            upiMandate.setOrgTxnId(reqMandate.getTxn().getId());
//            upiMandate.setSequence("0");
//        }
//        LOGGER.info("getMandateDetails: txn ids ");
//        upiMandate.setCustRef(reqMandate.getTxn().getCustRef());
//        upiMandate.setRefId(reqMandate.getTxn().getRefId());
//        upiMandate.setRefCategory(reqMandate.getTxn().getRefCategory());
//        upiMandate.setTxnNote(reqMandate.getTxn().getNote());
//        upiMandate.setMandateType(reqMandate.getTxn().getType().value());
//        upiMandate.setInitMode(reqMandate.getTxn().getInitiationMode());
//        upiMandate.setInitiateBy(reqMandate.getTxn().getInitiatedBy().value());
//        upiMandate.setPurpose(reqMandate.getTxn().getPurpose());
//        upiMandate.setUmn(reqMandate.getMandate().getUmn());
//        LOGGER.info("getMandateDetails: before revokeable");
//        upiMandate.setRevokable(reqMandate.getMandate().getRevokeable().value());
//        if (reqMandate.getMandate().getShareToPayee() != null) {
//            upiMandate.setShareToPayee(reqMandate.getMandate().getShareToPayee().value());
//        }
//        LOGGER.info("getMandateDetails: before blockFund");
//        if (reqMandate.getMandate().getBlockFund() != null) {
//            upiMandate.setBlockFund(reqMandate.getMandate().getBlockFund().value());
//        }
//        upiMandate.setStartDate(reqMandate.getMandate().getValidity().getStart());
//        upiMandate.setEndDate(reqMandate.getMandate().getValidity().getEnd());
//        LOGGER.info("getMandateDetails: before amount");
//        upiMandate.setAmount(new BigDecimal(reqMandate.getMandate().getAmount().getValue()));
//        upiMandate.setAmountPaisa(UpiUtils.convertAmountInPaisa(reqMandate.getMandate().getAmount().getValue()));
//        upiMandate.setAmountRule(reqMandate.getMandate().getAmount().getRule().value());
//        upiMandate.setRecurrencePattern(reqMandate.getMandate().getRecurrence().getPattern().value());
//        if (reqMandate.getMandate().getRecurrence().getRule() != null) {
//            upiMandate.setRecurrenceRuleValue(reqMandate.getMandate().getRecurrence().getRule().getValue());
//            upiMandate.setRecurrenceRuleType(reqMandate.getMandate().getRecurrence().getRule().getType().value());
//        }
//        LOGGER.info("getMandateDetails: mandate fields ");
//        upiMandate.setPayerVpa(reqMandate.getPayer().getAddr());
//        upiMandate.setPayerName(reqMandate.getPayer().getName());
//        upiMandate.setPayerMccCode(reqMandate.getPayer().getCode());
//
//        Optional.ofNullable(reqMandate.getPayer().getDevice()).ifPresent(f -> {
//            String payerdevice = f.getTag().stream().map(m -> {
//                if (DeviceTagNameType.MOBILE.equals(m.getName())) {
//                    upiMandate.setPayerMobileNo(m.getValue());
//                }
//                StringBuilder builder = new StringBuilder();
//                builder.append(m.getName().value()).append("~").append(m.getValue());
//                return builder.toString();
//            }).collect(Collectors.joining("|"));
//            upiMandate.setPayerDeviceDetails(payerdevice);
//        });
//        Optional.ofNullable(reqMandate.getPayer().getAc()).ifPresent(f -> {
//            getPayerAcc(f, upiMandate);
//
//        });
//        LOGGER.info("getMandateDetails: payer info");
//        Optional.ofNullable(reqMandate.getPayees()).ifPresent(p -> {
//            PayeeType payee = p.getPayee().stream().findFirst().get();
//            upiMandate.setPayeeVpa(payee.getAddr());
//            upiMandate.setPayeeName(payee.getName());
//            upiMandate.setPayeeMccCode(payee.getCode());
//
//            Optional.ofNullable(payee.getDevice()).ifPresent(f -> {
//                String payeedevice = f.getTag().stream().map(m -> {
//                    if (DeviceTagNameType.MOBILE.equals(m.getName())) {
//                        upiMandate.setPayeeMobileNo(m.getValue());
//                    }
//                    StringBuilder builder = new StringBuilder();
//                    builder.append(m.getName().value()).append("~").append(m.getValue());
//                    return builder.toString();
//                }).collect(Collectors.joining("|"));
//                upiMandate.setPayeeDeviceDetails(payeedevice);
//            });
//            Optional.ofNullable(payee.getAc()).ifPresent(f -> {
//                getPayeeAcc(f, upiMandate);
//
//            });
//            LOGGER.info("getMandateDetails: payee info");
//        });
//
//        upiMandate.setStatus(Constants.MANDATE_PENDING);
//        upiMandate.setStartDatetime(new Date());
//        upiMandate.setUpdateTime(new Date());
//        LOGGER.info("getMandateDetails: before expiry");
//        upiMandate.setExpiry(UpiUtils.getExpiryTime("ddMMyyyy", reqMandate.getMandate().getValidity().getEnd()));
//        if (Constants.PAUSE.equals(reqMandate.getTxn().getType().value())) {
//            upiMandate.setPauseStartDate(reqMandate.getMandate().getValidity().getStart());
//            upiMandate.setPauseEndDate(reqMandate.getMandate().getValidity().getEnd());
//            upiMandate
//                    .setPauseExpiry(UpiUtils.getExpiryTime("ddMMyyyy", reqMandate.getMandate().getValidity().getEnd()));
//            upiMandate.setPaused(true);
//        }
//
//        upiMandate.setHeaderTs(reqMandate.getHead().getTs());
//        LOGGER.info("upiMandate {}", upiMandate);
//        return upiMandate;
//    } catch (Exception e) {
//        LOGGER.error("getMandateDetails failed for txnId={}", reqMandate.getTxn() != null ? reqMandate.getTxn().getId() : "unknown", e);
//        throw e;
//
//    }
//}

