function y=path()
    camera=1;
    cale=[];
    while (camera~=46)
        cale(size(cale)+1)=camera;
        camera=next(camera);
    end
end