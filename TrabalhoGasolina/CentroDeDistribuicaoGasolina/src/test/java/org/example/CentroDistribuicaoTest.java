package org.example;

import org.example.CentroDistribuicao.SITUACAO;
import org.example.CentroDistribuicao.TIPOPOSTO;
import org.junit.Assert;
import org.junit.Test;

public class CentroDistribuicaoTest {

    private CentroDistribuicao cd = null;

    void setUpTanqueNormal() {
        int tAditivo = 500;
        int tGasolina = 100000;
        int tAlcool1 = 1250;
        int tAlcool2 = 1250;

        cd = new CentroDistribuicao(tAditivo, tGasolina, tAlcool1, tAlcool2);
        SITUACAO situacaoTanqueCheio = SITUACAO.NORMAL;
    }

    void setUpTanqueNormalSemSituacao() {
        int tAditivo = 500;
        int tGasolina = 100000;
        int tAlcool1 = 1250;
        int tAlcool2 = 1250;

        cd = new CentroDistribuicao(tAditivo, tGasolina, tAlcool1, tAlcool2);
    }

    void setUpTanqueSobraviso() {
        int tAditivo = 300;
        int tGasolina = 4000;
        int tAlcool1 = 1000;
        int tAlcool2 = 1000;

        cd = new CentroDistribuicao(tAditivo, tGasolina, tAlcool1, tAlcool2);
        SITUACAO situacaoTanqueSobraviso = SITUACAO.SOBRAVISO;
    }

    void setUpTanqueSobravisoSemSituacao() {
        int tAditivo = 300;
        int tGasolina = 4000;
        int tAlcool1 = 1000;
        int tAlcool2 = 1000;

        cd = new CentroDistribuicao(tAditivo, tGasolina, tAlcool1, tAlcool2);
    }

    void setUpTanqueEmergencia() {
        int tAditivo = 100;
        int tGasolina = 3500;
        int tAlcool1 = 900;
        int tAlcool2 = 900;

        cd = new CentroDistribuicao(tAditivo, tGasolina, tAlcool1, tAlcool2);
        SITUACAO situacaoTanqueEmergencia = SITUACAO.EMERGENCIA;
    }

    void setUpTanqueEmergenciaSemSituacao() {
        int tAditivo = 100;
        int tGasolina = 3500;
        int tAlcool1 = 900;
        int tAlcool2 = 900;

        cd = new CentroDistribuicao(tAditivo, tGasolina, tAlcool1, tAlcool2);

    }

    @Test
    public void defineSituacaoNormalTest() {

        setUpTanqueNormalSemSituacao();
        Assert.assertEquals(SITUACAO.NORMAL, cd.getSituacao());
    }

    @Test
    public void defineSituacaoSobravisoTest() {

        setUpTanqueSobravisoSemSituacao();
        Assert.assertEquals(SITUACAO.SOBRAVISO, cd.getSituacao());
    }

    @Test
    public void defineSituacaoEmergenciaTest() {

        setUpTanqueEmergenciaSemSituacao();
        Assert.assertEquals(SITUACAO.EMERGENCIA, cd.getSituacao());
    }

    @Test
    public void getSituacaoNormalTest() {

        setUpTanqueNormal();
        Assert.assertEquals(SITUACAO.NORMAL, cd.getSituacao());
    }

    @Test
    public void getSituacaoSobravisoTest() {

        setUpTanqueSobraviso();
        SITUACAO resultSobraviso = cd.getSituacao();
        Assert.assertEquals(SITUACAO.SOBRAVISO, resultSobraviso);
    }

    @Test
    public void getSituacaoEmergenciaTest() {
        setUpTanqueEmergencia();
        SITUACAO resultEmergencia = cd.getSituacao();
        Assert.assertEquals(SITUACAO.EMERGENCIA, resultEmergencia);
    }

    @Test
    public void gettGasolinaTest() {

        setUpTanqueNormal();
        int result = cd.gettGasolina();
        Assert.assertEquals(10000, result);

    }

    @Test
    public void gettAditivoTest() {

        setUpTanqueNormal();
        int result = cd.gettAditivo();
        Assert.assertEquals(500, result);

    }

    @Test
    public void gettAlcool1Test() {

        setUpTanqueNormal();
        int result = cd.gettAlcool1();
        Assert.assertEquals(1250, result);

    }

    @Test
    public void gettAlcool2Test() {

        setUpTanqueNormal();
        int result = cd.gettAlcool2();
        Assert.assertEquals(1250, result);

    }

    @Test
    public void recebeAditivoTest() {

        setUpTanqueSobraviso();
        int result = cd.recebeAditivo(100);
        Assert.assertEquals(100, result);

    }

    @Test
    public void recebeAditivoValorInvalidoTest() {

        setUpTanqueNormal();
        int result = cd.recebeAditivo(150);
        Assert.assertEquals(-1, result);

    }

    @Test
    public void recebeGasolinaTest() {

        setUpTanqueSobraviso();
        int result = cd.recebeGasolina(1000);
        Assert.assertEquals(1000, result);

    }

    @Test
    public void recebeGasolinaValorInvalidoTest() {

        setUpTanqueNormal();
        int result = cd.recebeGasolina(1500);
        Assert.assertEquals(-1, result);

    }

    @Test
    public void recebeAlcoolTest() {

        setUpTanqueSobraviso();
        int result = cd.recebeGasolina(200);
        Assert.assertEquals(200, result);

    }

    @Test
    public void recebeAlcoolValorInvalidoTest() {

        setUpTanqueNormal();
        int result = cd.recebeAlcool(150);
        Assert.assertEquals(-1, result);

    }

    @Test
    public void encomendaCombustivelComTanqueNormalEmPostoComumTest() {
        setUpTanqueNormal();
        int[] expectedResult = { 485, 9790, 1175, 1250 };
        int[] result = cd.encomendaCombustivel(300, TIPOPOSTO.COMUM);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void encomendaCombustivelComTanqueNormalEmPostoComumEstrategico() {
        setUpTanqueNormal();
        int[] expectedResult = { 485, 9790, 1175, 1250 };
        int[] result = cd.encomendaCombustivel(300, TIPOPOSTO.ESTRATEGICO);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void encomendaCombustivelComTanqueSobravisoEmPostoComumTest() {
        setUpTanqueSobraviso();
        int[] expectedResult = { 292, 3895, 963, 1250 };
        int[] result = cd.encomendaCombustivel(300, TIPOPOSTO.COMUM);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void encomendaCombustivelComTanqueSobravisoEmPostoEstrategicoTest() {
        setUpTanqueSobraviso();
        int[] expectedResult = { 285, 3790, 925, 1250 };
        int[] result = cd.encomendaCombustivel(300, TIPOPOSTO.ESTRATEGICO);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void encomendaCombustivelComTanqueEmergenciaEmPostoComumTest() {
        setUpTanqueEmergencia();
        int[] expectedResult = { -14, 3395, 0, 863 };
        int[] result = cd.encomendaCombustivel(300, TIPOPOSTO.COMUM);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void encomendaCombustivelComTanqueEmergenciaEmPostoEstrategicoTest() {
        setUpTanqueEmergencia();
        int[] expectedResult = { 93, 3395, 0, 863 };
        int[] result = cd.encomendaCombustivel(300, TIPOPOSTO.ESTRATEGICO);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void encomendaCombustivelValorInvalidoTest() {
        setUpTanqueNormal();
        int[] expectedResult = { -7, 9790, 1175, 1250 };
        int[] result = cd.encomendaCombustivel(-300, TIPOPOSTO.COMUM);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void encomendaCombustivelReservaInsuficientelTest() {
        setUpTanqueNormal();
        int[] expectedResult = { -21, 9790, 1175, 1250 };
        int[] result = cd.encomendaCombustivel(20000, TIPOPOSTO.COMUM);
        Assert.assertEquals(expectedResult, result);
    }

}
