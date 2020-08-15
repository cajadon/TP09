Function Input-Options
{ 
    Write-Host "#  " -ForegroundColor Yellow  -NoNewline; Write-Host "Option Details" -ForegroundColor Green
    Write-Host "1. " -ForegroundColor Yellow -NoNewline; Write-Host "Data Analysis" -ForegroundColor Green
    Write-Host ""
    Write-Host "9. " -ForegroundColor Yellow -NoNewline; Write-Host "Press 9 to exit" -ForegroundColor Gray
    Write-Host ""
}

while($true) {
    Input-Options
    $choice= Read-Host "Please select one of the above options :"
    if($choice -eq 1){
		git pull origin master
        mvn install '-DskipSurefireReport=true' '-DtestSuiteFile=TestExecution.xml' 2>null
    }elseif($choice -eq 9){
        Exit
    } else{
        Write-Host ""
        Write-Host 'Wrong input, try again.' -ForegroundColor Red 
    }
}